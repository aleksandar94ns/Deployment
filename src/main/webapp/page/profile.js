app.controller('ProfileController', function ($scope, $state, $http, $mdDialog, friendsService) {

    $scope.page.current = 1;

    $scope.pendingFriendships = [];
    $scope.acceptedFriendships = [];

    $scope.loadData = function() {
        friendsService.list(function (response) {
            $scope.pendingFriendships = response.data.filter(function(friendship) {
                return friendship.status === 'PENDING';
            });
            $scope.acceptedFriendships = response.data.filter(function(friendship) {
                return friendship.status === 'ACCEPTED';
            });
        });
    };

    $scope.loadData();

    $scope.changePassword = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/changePassword.html',
            controller: 'ChangePasswordController'
        });
    };

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
        }).finally(function () {
            $scope.loadData();
        });
    };

    $scope.respond = function(friendship) {

    };
    
    $scope.cancel = function (friendship) {
        friendsService.delete(friendship.id, function() {
            $scope.loadData();
        });
    };

    // Respond menu

    var originatorEv;

    $scope.openMenu = function($mdOpenMenu, ev) {
        originatorEv = ev;
        $mdOpenMenu(ev);
    };

    $scope.accept = function(friendship, ev) {
        friendship.status = 'ACCEPTED';
        friendsService.patch(friendship, function () {
            $scope.loadData();
        });
        originatorEv = null;
    };

    $scope.decline = function(friendship, ev) {
        friendship.status = 'DECLINED';
        friendsService.patch(friendship, function () {
            $scope.loadData();
        });
        originatorEv = null;
    };
});