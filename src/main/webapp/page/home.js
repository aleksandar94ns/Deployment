app.controller('HomeController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;
});
