/**
 * Created by Ivan on 4/2/2015.
 */

var app = angular.module('ats', [
    'ngCookies',
    'ngResource',
    'ngSanitize',
    'ngRoute'
]);

app.config(function ($routeProvider) {
    $routeProvider.when('/', {
        templateUrl: 'views/mainPage.html'//,
        //controller: 'MainPageCtrl'
    }).when('/createApplicant', {
        templateUrl: 'views/newApplicant.html',
        controller: 'CreateCtrl'
    }).when('/allApplicants', {
        templateUrl: 'views/allApplicants.html',
        controller: 'AllApplicantsCtrl'
    }).otherwise({
        redirectTo: '/'
    })
});

app.controller('AllApplicantsCtrl', function ($scope, $http) {
    $http.get('/applicants').success(function (data) {
        $scope.applicants = data;
    }).error(function (data, status) {
        console.log('Error ' + data)
    })

    /*$scope.todoStatusChanged = function (todo) {
        console.log(todo);
        $http.put('/api/v1/todos/' + todo.id, todo).success(function (data) {
            console.log('status changed');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }*/
});

app.controller('CreateCtrl', function ($scope, $http, $location) {
    $scope.todo = {
        done: false
    };

    $scope.createTodo = function () {
        console.log($scope.todo);
        $http.post('/api/v1/todos', $scope.todo).success(function (data) {
            $location.path('/');
        }).error(function (data, status) {
            console.log('Error ' + data)
        })
    }
});