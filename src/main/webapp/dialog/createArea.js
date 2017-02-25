app.controller('CreateAreaController', function ($scope, $http, $state, $mdDialog, restaurantsService, areasService) {

    $scope.submit = function () {
        areasService.create($scope.area, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});