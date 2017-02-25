app.controller('SuppliesController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, suppliesService) {

    $scope.page.current = 12;

    suppliesService.list(function (response) {
        $scope.supplies = response.data;
    });

    $scope.addSupply = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createSupply.html',
            controller: 'CreateSupplyController'
        }).finally(function () {
            suppliesService.list(function (response) {
                $scope.supplies = response.data;
            });
        });
    };
});