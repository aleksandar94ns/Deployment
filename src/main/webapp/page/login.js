app.controller('LoginController', function ($scope, $state, $http, $mdDialog, authenticationService, usersService) {

    $scope.submit = function () {
        authenticationService.login($scope.user, function () {
            $http.get('/api/users/me')
                .success(function (user) {
                    authenticationService.setUser(user);
                    if (authenticationService.isSystemManager()) {
                        $state.transitionTo('navigation.restaurantManagers');
                    } else if (authenticationService.isManager()) {
                        $state.transitionTo('navigation.employees');
                    } else if (authenticationService.isSeller()){
                        usersService.getMe(function (response) {
                            $scope.seller = response.data;
                            if ($scope.seller.active == false){
                                $state.transitionTo('navigation.supplies');
                            } else {
                                $state.transitionTo('navigation.changePasswordSeller');
                            }
                        });
                    } else {
                        $state.transitionTo('navigation.home');
                    }
                })
                .error(error);
        }, error);
    };

    var error = function () {
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .title('Login failed!')
                .content('Wrong username or password.')
                .ok('Ok')
        );
    };

    $scope.create = function () {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createAccount.html',
            controller: 'CreateAccountController'
        }).finally(function() {
        });
    }
});