package com.saprykin.ats;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.saprykin.ats.configuration.AppConfig;
import com.saprykin.ats.model.Applicant;
import com.saprykin.ats.model.Pool;
import com.saprykin.ats.model.Role;
import com.saprykin.ats.model.User;
import com.saprykin.ats.service.ApplicantService;
import com.saprykin.ats.service.PoolService;
import com.saprykin.ats.service.RoleService;
import com.saprykin.ats.service.UserService;

import com.saprykin.ats.util.JsonAddNewApplicant;
import com.saprykin.ats.util.JsonTransformer;
import com.saprykin.ats.util.UserDetails;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import spark.Request;
import spark.Response;
import spark.Route;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;

// Document Object
import com.itextpdf.text.Document;
//For adding content into PDF document
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.DocumentException;

import static spark.Spark.before;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(java.lang.String[] args) {

        // Observe: this method must be called before all other methods.
        staticFileLocation("/public"); // Static files

        setPortForApp();
        setUpLog4jProperties();

        AbstractApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = (UserService) context.getBean("userService");
        RoleService roleService = (RoleService) context.getBean("roleService");

        Role userRole = new Role();
        userRole.setName("user");
        Role adminRole = new Role();
        adminRole.setName("admin");

        roleService.saveRole(userRole);
        roleService.saveRole(adminRole);

        Random random = new Random();

        User user1 = new User();
        user1.setEmail("user@bar.com");
        user1.setEmailConfirmation(false);
        user1.setEmailNotifications(false);
        user1.setRole(userRole);

        User user2 = new User();
        user2.setEmail("admin@foo.com");
        user2.setEmailConfirmation(true);
        user2.setEmailNotifications(true);
        user2.setRole(adminRole);

        userService.saveUser(user1);
        userService.saveUser(user2);


        ApplicantService applicantService = (ApplicantService) context.getBean("applicantService");

        Applicant applicant2 = new Applicant();
        applicant2.setDateOfBirth(LocalDate.now());
        applicant2.setFirstName("Ivan");
        applicant2.setLastName("Ivanov");
        applicant2.setMiddleName("Ivanovich");
        applicant2.setFullAddress("Sevastopol, yl Gogolya 13, kv 8");

        Applicant applicant1 = new Applicant();
        applicant1.setDateOfBirth(LocalDate.now());
        applicant1.setFirstName("Ivan");
        applicant1.setLastName("Saprykin");
        applicant1.setMiddleName("Igorevich");
        applicant1.setFullAddress("Sevastopol, yl Yniversitetskaya 26, kv 1676");

        applicantService.saveApplicant(applicant1);
        applicantService.saveApplicant(applicant2);

        // ... check if we remember this user
        before((request, response) -> {

            logger.info("Before filter /   starts...");
            boolean userIsRemembered = false;
            String userEmail = request.cookie("userEmailSurveyApp");
            User user = null;
            if(userEmail != null) {
                user = userService.findUserByEmail(userEmail);
                if(user != null) {
                    logger.info("Fined user email in cookie");
                    userIsRemembered = true;
                }
            }

            if(userIsRemembered) {
                logger.info("User details if any: " + request.session().attribute("user"));
                if(null == request.session().attribute("user")) { // TODO
                    UserDetails userDetails = new UserDetails(user.getId(), user.getRole().getName());
                    request.session().attribute("user", userDetails);
                    logger.info("Add user details in session");
                } else {
                    logger.info("User details is already in session");
                }
            } else {
                logger.info("User is new");
            }
            logger.info("Before filter /   ends...");
        });

        post("/createApplicant", "application/json", (request, response) -> {
            BufferedReader br = null;
            JsonAddNewApplicant jsonAddNewApplicant;
            try {
                br = new BufferedReader(new InputStreamReader(request.raw().getInputStream()));
                java.lang.String json = br.readLine();

                // 2. initiate jackson mapper
                ObjectMapper mapper = new ObjectMapper();

                // 3. Convert received JSON to String
                jsonAddNewApplicant = mapper.readValue(json, JsonAddNewApplicant.class);
            } catch(IOException e) {
                String exceptionString = "Some shit happened while trying to get user input from JSON file!!!!!" + e.toString();
                logger.error(exceptionString);
                return exceptionString;
            }

            // validate userInputString

            String validatedUserFirstName = "fn";
            validatedUserFirstName = jsonAddNewApplicant.getFirstName();
            String validatedUserLastName = "ln";
            validatedUserLastName = jsonAddNewApplicant.getLastName();

            // check if user with inputted e-mail exist in DB
            Applicant applicant = new Applicant();
            applicant.setFirstName(validatedUserFirstName);
            applicant.setLastName(validatedUserLastName);
            applicant.setDateOfBirth(LocalDate.now());

            applicantService.saveApplicant(applicant);

            return "Success";


        }, new JsonTransformer());

        get("/applicants", "application/json", (request, response) -> {
            logger.info("Called hhtp GET method    /applicants");

            return applicantService.findAllApplicants();
        }, new JsonTransformer());

        get("/pdfapplicants", "application/pdf", (request, response) -> {
            logger.info("Called hhtp GET method    /pdfapplicants");

            //Set content type to application / pdf
            //browser will open the document only if this is set
            response.raw().setContentType("application/pdf");
            //Get the output stream for writing PDF object

            OutputStream out = response.raw().getOutputStream();
            try {
                Document document = new Document();
            /* Basic PDF Creation inside servlet */
                /*
                BaseFont helvetica =
                    BaseFont.createFont(
                        BaseFont.HELVETICA,
                         BaseFont.CP1251,
                        BaseFont.NOT_EMBEDDED);
                Font font = new Font(helvetica, 12);
                String text1 = "бла-бла-бла";
                document.add(new Paragraph(text1, font));
                 */
                PdfWriter.getInstance(document, out);
                document.open();
                document.add(new Paragraph("Plain text"));
                document.add(new Paragraph("Список абитуриентов:"));

                List<Applicant> applicantList = applicantService.findAllApplicants();
                for(Applicant applicant : applicantList) {
                    document.add(new Paragraph("Абитуриент:"));
                    document.add(new Paragraph(applicant.toString()));
                }
                document.add(new Paragraph("Plain text"));
                document.close();
            } catch(DocumentException exc) {
                throw new IOException(exc.getMessage());
            } finally {
                out.close();
            }

            //Set content type to application / pdf
            //browser will open the document only if this is set
            // response.raw().setContentType("application/pdf");
            //Get the output stream for writing PDF object

            //OutputStream out=response.raw().getOutputStream();
            //Document document;
//            try {
//                document = new Document();
//            /* Basic PDF Creation inside servlet */
//                //PdfWriter.getInstance(document, out);
//                document.open();
//                document.add(new Paragraph("Plain text"));
//                document.add(new Paragraph("Список абитуриентов:"));
//
//                List<Applicant> applicantList = applicantService.findAllApplicants();
//                for(Applicant applicant: applicantList) {
//                    document.add(new Paragraph("Абитуриент:"));
//                    document.add(new Paragraph(applicant.toString()));
//                }
//                document.add(new Paragraph("Plain text"));
//                document.close();
//            }
//            catch (DocumentException exc){
//                throw new IOException(exc.getMessage());
//            }
//            finally {
//                //out.close();
//            }


            return response;
        });


        get("/users", "application/json", (request, response) -> {
            logger.info("Called hhtp GET method    /users");

            return userService.findAllUsers();
        }, new JsonTransformer());

        get("/info", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

                return request.session().attribute("user");
            }
        }, new JsonTransformer());


        // TODO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        post("/login", (req, res) -> {

            logger.info("Called hhtp POST method   /login");

            BufferedReader br = null;
            String userInputString = "";
            try {
                br = new BufferedReader(new InputStreamReader(req.raw().getInputStream()));
                java.lang.String json = br.readLine();

                // 2. initiate jackson mapper
                ObjectMapper mapper = new ObjectMapper();

                // 3. Convert received JSON to String
                userInputString = mapper.readValue(json, String.class);
            } catch(IOException e) {
                String exceptionString = "Some shit happened while trying to get user input from JSON file!!!!!" + e.toString();
                logger.error(exceptionString);
                return exceptionString;
            }

            // validate userInputString
            String validatedUserEmail = "";
            validatedUserEmail = userInputString;

            // check if user with inputted e-mail exist in DB
            boolean userExist = false;
            User user = null;
            if(validatedUserEmail != null) {
                user = userService.findUserByEmail(validatedUserEmail);
                if(user != null) {
                    logger.info("Fined user with login email in DB");
                    userExist = true;
                }
            }

            // add cookie
            res.cookie("userEmailSurveyApp", validatedUserEmail, 60 * 60 * 24 * 30, true);

            // set session attribute
            if(userExist) {
                UserDetails userDetails = new UserDetails(user.getId(), user.getRole().getName());
                req.session().attribute("user", userDetails);
                logger.info("Add user details after logging in session");
                return userDetails;
            } else {
                return "User with e-mail:" + userInputString + " does not exist!";
            }

        }, new JsonTransformer());

        /*post("/login", (request, response) -> {
            logger.info("Called hhtp GET method   /login");


            BufferedReader br = new BufferedReader(new InputStreamReader(request.raw().getInputStream()));
            String json = br.readLine();

            // 2. initiate jackson mapper
            ObjectMapper mapper = new ObjectMapper();

            // 3. Convert received JSON to String
            String userInputString = mapper.readValue(json, String.class);

            Session session = request.session();
            logger.info("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! session.isNew = " + session.isNew());
            session.attribute("userAuthentication", true);
            session.attribute("userRole", "user");
            session.attribute("userEmail", userInputString);

            return null;
        });*/
    }

    /**
     * Heroku assigns different port each time, hence reading it from process.
     */
    private static void setPortForApp() {
        logger.info("setting port for application...");
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if(process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8080;
        }
        setPort(port);
        logger.info("port is: " + port);
    }

    private static void setUpLog4jProperties() {
        java.lang.String log4jConfPath = "web/WEB-INF/classes/log4j.properties";
        PropertyConfigurator.configure(log4jConfPath);
    }

}

/*
//Set content type to application / pdf
            //browser will open the document only if this is set
            response.raw().setContentType("application/pdf");
            //Get the output stream for writing PDF object

            OutputStream out=response.raw().getOutputStream();
            try {
                Document document = new Document();
            /* Basic PDF Creation inside servlet */
/*PdfWriter.getInstance(document, out);
        document.open();
        document.add(new Paragraph("Plain text"));
        document.add(new Paragraph("Список абитуриентов:"));

        List<Applicant> applicantList = applicantService.findAllApplicants();
        for(Applicant applicant: applicantList) {
        document.add(new Paragraph("Абитуриент:"));
        document.add(new Paragraph(applicant.toString()));
        }
        document.add(new Paragraph("Plain text"));
        document.close();
        }
        catch (DocumentException exc){
        throw new IOException(exc.getMessage());
        }
        finally {
        out.close();
        }*/
