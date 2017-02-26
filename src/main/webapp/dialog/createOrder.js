app.controller('CreateOrderController', function ($rootScope, $scope, $stateParams, $http, $state, $mdDialog, reservation, menusService, drinkCardsService) {

    var loadData = function () {
        menusService.listByRestaurant(reservation.reservation.restaurant.id, function (response) {
            $scope.menus = response.data;
        });
        drinkCardsService.listByRestaurant(reservation.reservation.restaurant.id, function (response) {
            $scope.drinkCards = response.data;
        });
    };

    loadData();

    $scope.close = function () {
        $mdDialog.hide();
    };
});