app.controller('CreateChefController', function ($scope, $http, $state, $mdDialog, restaurantsService, chefsService, authenticationService) {

    $scope.manager = authenticationService.getUser();

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.queryRestaurants = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.restaurants.length; i++) {
            if ($scope.restaurants[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.restaurants[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        chefsService.create($scope.user, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});