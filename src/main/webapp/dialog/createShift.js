app.controller('CreateShiftController', function ($scope, $http, $state, $mdDialog, restaurantsService, shiftsService) {

    $scope.submit = function () {
        $scope.shift.startHour.toString();
        shiftsService.create($scope.shift, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});