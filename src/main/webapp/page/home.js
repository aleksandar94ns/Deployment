app.controller('HomeController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService, restaurantsService) {

    $scope.page.current = 0;

    $scope.authService = authenticationService;

    restaurantsService.listVisited(function(response) {
       $scope.restaurants = response.data;
    });
});
