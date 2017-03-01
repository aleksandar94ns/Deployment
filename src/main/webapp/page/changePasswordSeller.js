app.controller('ChangePasswordSellerController', function ($scope, $state, $http, $mdDialog, usersService) {

    $scope.close = function () {
        $state.transitionTo('navigation.supplies');
    };

    $scope.submit = function () {
        usersService.changePassword($scope.userCredentials, function() {
            $scope.close();
        });
    };

});