app.controller('CreateSystemManagerController', function ($scope, $http, $state, $mdDialog, restaurantsService, systemManagersService) {

    $scope.submit = function () {
        systemManagersService.create($scope.user, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});