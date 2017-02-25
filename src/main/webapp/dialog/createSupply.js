app.controller('CreateSupplyController', function ($scope, $http, $state, $mdDialog, restaurantsService, suppliesService) {

    $scope.submit = function () {
        $scope.supply.expiration = moment($scope.expiration).format('YYYY-MM-DD');
        suppliesService.create($scope.supply, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});