app.controller('RestaurantsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdSidenav, $mdDialog, $interval, authenticationService, restaurantsService) {

    $scope.page.current = 1;

    $scope.authService = authenticationService;

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.addRestaurant = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createRestaurant.html',
            controller: 'CreateRestaurantController'
        }).finally(function () {
            restaurantsService.list(function(response) {
                $scope.restaurants = response.data;
            });
        });
    };

});
