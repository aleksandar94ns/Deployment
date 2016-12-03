app.controller('ProfileController', function ($scope, $state, $http, $mdDialog, friendsService) {

    $scope.page.current = 1;

    $scope.friends = [];

    friendsService.list(function (response) {
        $scope.friends = response.data;
    });

    $scope.edit = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/editAccount.html',
            controller: 'EditAccountController'
        });
    };

    $scope.addFriend = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createFriendship.html',
            controller: 'CreateFriendshipController'
        });
    }
});