app.controller('CreateFriendshipController', function ($scope, $stateParams, $http, $state, $mdDialog, friendsService, usersService) {

    friendsService.listPotentialFriends(function(response) {
        $scope.users = response.data;
    });

    $scope.queryUsers = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.users.length; i++) {
            if ($scope.users[i].firstName.toLowerCase().match(searchText.toLowerCase()) ||
                $scope.users[i].lastName.toLowerCase().match(searchText.toLowerCase()) ||
                searchText.toLowerCase().match($scope.users[i].firstName.toLowerCase()) ||
                searchText.toLowerCase().match($scope.users[i].lastName.toLowerCase())) {
                queryResults.push($scope.users[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        friendsService.create($scope.selectedUser, function() {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };

});
