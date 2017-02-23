app.controller('CreateDrinkItemController', function ($scope, $http, $state, $mdDialog, drinkCardsService, drinkItemsService) {

    drinkCardsService.list(function(response) {
        $scope.drinkCards = response.data;
    });

    $scope.queryDrinkCards = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.drinkCards.length; i++) {
            if ($scope.drinkCards[i].name.toLowerCase().match(searchText.toLowerCase())) {
                queryResults.push($scope.drinkCards[i]);
            }
        }
        return queryResults;
    };

    $scope.submit = function () {
        drinkItemsService.create($scope.drinkItem, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});