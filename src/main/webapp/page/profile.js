app.controller('ProfileController', function ($scope, $state, $http, $mdDialog, authenticationService) {

    $scope.page.current = 1;

    $scope.edit = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/editccount.html',
            controller: 'EditAccountController'
        }).finally(function() {
        });
    };

});