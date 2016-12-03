app.controller('CreateFriendshipController', function ($scope, $stateParams, $http, $state, $mdDialog, friendsService, usersService) {

    friendsService.listPotentialFriends(function(response) {
        $scope.users = response.data;
    });

    $scope.queryUsers = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.users.length; i++) {
            if ($scope.users[i].firstName.match(searchText) || $scope.users[i].lastName.match(searchText)
                || searchText.match($scope.users[i].firstName) || searchText.match($scope.users[i].lastName)) {
                queryResults.push($scope.users[i]);
            }
        }
        return queryResults;
    }

    $scope.submit = function () {
        friendsService.create($scope.selectedUser, function() {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };

});
