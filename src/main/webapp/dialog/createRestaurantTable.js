app.controller('CreateRestaurantTableController', function ($scope, $http, $state, $mdDialog, areasService, restaurantTablesService) {

    areasService.list(function(response) {
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
        restaurantTablesService.create($scope.restaurantTable, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});