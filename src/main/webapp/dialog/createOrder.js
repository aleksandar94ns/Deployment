app.controller('CreateOrderController', function ($rootScope, $scope, $stateParams, $http, $state, $mdDialog, reservation, menusService, drinkCardsService, ordersService) {

    $scope.order = {
        reservation: reservation.reservation,
        orderItems: []
    };

    var loadData = function () {
        menusService.listByRestaurant(reservation.reservation.restaurant.id, function (response) {
            $scope.menus = response.data;
        });
        drinkCardsService.listByRestaurant(reservation.reservation.restaurant.id, function (response) {
            $scope.drinkCards = response.data;
        });
    };

    loadData();

    $scope.submit = function () {
        var menuItems = [];
        for (var i in $scope.menus) {
            menuItems.push.apply(menuItems, $scope.menus[i].menuItems.filter(function (menuItem) {
                return menuItem.selected;
            }))
        }
        for (var i in menuItems) {
            $scope.order.orderItems.push({
                note: "",
                menuItem: menuItems[i]
            });
        }
        var drinkItems = [];
        for (var i in $scope.drinkCards) {
            drinkItems.push.apply(drinkItems, $scope.drinkCards[i].drinkItems.filter(function (drinkItem) {
                return drinkItem.selected;
            }))
        }
        for (var i in drinkItems) {
            $scope.order.orderItems.push({
                note: "",
                menuItem: drinkItems[i]
            });
        }
        ordersService.create($scope.order, function () {
            $mdDialog.hide();
        });
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});