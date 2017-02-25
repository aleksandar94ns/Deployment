app.controller('AreasController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, areasService) {

    $scope.page.current = 14;

    areasService.list(function (response) {
        $scope.areas = response.data;
    });

    $scope.addArea = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createArea.html',
            controller: 'CreateAreaController'
        }).finally(function () {
            areasService.list(function (response) {
                $scope.areas = response.data;
            });
        });
    };

    $scope.addRestaurantTable = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createRestaurantTable.html',
            controller: 'CreateRestaurantTableController'
        });
    };
});