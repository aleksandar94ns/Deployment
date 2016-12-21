app.controller('RateRestaurantController', function ($scope, $state, $http, $mdDialog, reservation) {

    $scope.grade = {
        restaurant: 5,
        meal: 5,
        waiter: 5,
        reservation: reservation
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});