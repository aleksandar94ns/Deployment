app.controller('RestaurantTypesController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, restaurantTypesService) {

    $scope.page.current = 13;

    restaurantTypesService.list(function (response) {
        $scope.restaurantTypes = response.data;
    });

    $scope.addRestaurantType = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createRestaurantType.html',
            controller: 'CreateRestaurantTypeController'
        }).finally(function () {
            restaurantTypesService.list(function (response) {
                $scope.restaurantTypes = response.data;
            });
        });
    };
});