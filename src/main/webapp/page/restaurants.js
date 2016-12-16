app.controller('RestaurantsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, restaurantsService) {

    $scope.page.current = 1;

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });
});
