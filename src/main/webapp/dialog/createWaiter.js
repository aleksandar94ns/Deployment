app.controller('CreateWaiterController', function ($scope, $http, $state, $mdDialog, areasService, waitersService, authenticationService, waiter) {

    $scope.user = waiter;

    $scope.manager = authenticationService.getUser();

    areasService.listByRestaurant($scope.manager.restaurant.id, function (response) {
        $scope.areas = response.data;
    });

    $scope.queryAreas = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.areas.length; i++) {
            if ($scope.areas[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.areas[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        waitersService.create($scope.user, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
