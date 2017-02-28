app.controller('RestaurantController', function ($scope, $state, $http, $mdDialog, restaurant) {

    $scope.restaurant = restaurant;

    $scope.close = function () {
        $mdDialog.hide();
    };
});