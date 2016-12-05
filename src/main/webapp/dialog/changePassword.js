/**
 * Created by Alex on 10/30/16.
 */
app.controller('ChangePasswordController', function ($scope, $state, $http, $mdDialog, usersService) {

    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.submit = function () {
        usersService.changePassword($scope.userCredentials, function() {
            $scope.close();
        });
    };

});