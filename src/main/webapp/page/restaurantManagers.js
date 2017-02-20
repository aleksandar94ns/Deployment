app.controller('RestaurantManagersController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, restaurantManagersService) {

    $scope.page.current = 0;

    restaurantManagersService.list(function (response) {
        $scope.restaurantManagers = response.data;
    });

    $scope.addRestaurantManager = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createRestaurantManager.html',
            controller: 'CreateRestaurantManagerController'
        }).finally(function () {
            restaurantManagersService.list(function (response) {
                $scope.restaurantManagers = response.data;
            });
        });
    };

});

