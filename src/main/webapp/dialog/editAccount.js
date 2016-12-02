/**
 * Created by Alex on 10/30/16.
 */
app.controller('EditAccountController', function ($scope, $state, $http, $mdDialog, authenticationService) {

    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.submit = function () {
        $http.patch('/api/users/guests', $scope.user).success(function () {
            $scope.close();
        }).error(function () {
            $scope.close();
        });
    };

});