app.controller('CreateRestaurantController', function ($scope, $state, $http, $mdDialog, restaurantTypesService, restaurantsService) {

    restaurantTypesService.list(function(response) {
        $scope.restaurantTypes = response.data;
    });

    $scope.queryTypes = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.restaurantTypes.length; i++) {
            if ($scope.restaurantTypes[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.restaurantTypes[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        restaurantsService.create($scope.restaurant, function() {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});