/**
 * Created by Alex on 10/30/16.
 */
app.controller('EditAccountController', function ($scope, $state, $http, $mdDialog, authenticationService, usersService) {

    $scope.user = authenticationService.getUser();
    $scope.authenticationService = authenticationService;

    $scope.close = function () {
        $mdDialog.hide();
    };

    $scope.submit = function () {
        usersService.edit($scope.user, function() {
            $scope.close();
        });
    };

});