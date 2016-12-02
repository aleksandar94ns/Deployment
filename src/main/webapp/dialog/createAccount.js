app.controller('CreateAccountController', function ($scope, $http, $state, $mdDialog) {

    $scope.username = {
        exists: true
    };

    $scope.submit = function () {
        $http.post('/api/users/guests', $scope.user).success(function () {
            $scope.close();
        }).error(function () {
            $scope.close();
        });
    };

    $scope.usernameChanged = function () {
        if ($scope.user) {
            $http.get('/api/users/' + $scope.user.username).success(function () {
                $scope.username.exists = true;
            }).error(function () {
                $scope.username.exists = false;
            });
        }
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
