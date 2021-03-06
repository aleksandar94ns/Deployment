app.controller('DrinkItemsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, drinkItemsService) {

    $scope.page.current = 8;

    drinkItemsService.list(function (response) {
        $scope.drinkItems = response.data;
    });

    $scope.addDrinkItem = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createDrinkItem.html',
            controller: 'CreateDrinkItemController'
        }).finally(function () {
            drinkItemsService.list(function (response) {
                $scope.drinkItems = response.data;
            });
        });
    };
});
