app.controller('CreateRestaurantTypeController', function ($scope, $http, $state, $mdDialog, restaurantsService, restaurantTypesService) {

    $scope.submit = function () {
        restaurantTypesService.create($scope.restaurantType, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});