app.controller('DrinkCardsController', function ($scope, $http, $state, $location, $log, $rootScope, $mdDialog, drinkCardsService) {

    $scope.page.current = 7;

    drinkCardsService.list(function (response) {
        $scope.drinkCards = response.data;
    });

    $scope.addDrinkCard = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createDrinkCard.html',
            controller: 'CreateDrinkCardController'
        }).finally(function () {
            drinkCardsService.list(function (response) {
                $scope.drinkCards = response.data;
            });
        });
    };
});