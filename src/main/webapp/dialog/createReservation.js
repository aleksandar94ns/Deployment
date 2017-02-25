app.controller('CreateReservationController', function ($scope, $stateParams, $http, $state, $mdDialog, restaurantsService) {

    restaurantsService.listVisited(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.restaurantSelected = function(restaurant) {
        $scope.restaurant  = restaurant;
        $scope.data.selectedIndex = 1;
        console.log($scope.restaurant);
    };


    $scope.close = function () {
        $mdDialog.hide();
    };
});
