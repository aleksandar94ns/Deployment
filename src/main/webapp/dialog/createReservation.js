app.controller('CreateReservationController', function ($scope, $stateParams, $http, $state, $mdDialog, restaurantsService) {

    restaurantsService.listVisited(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.restaurantSelected = function(restaurant) {
        $scope.restaurant  = restaurant;
        $scope.data.selectedIndex = 1;
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
