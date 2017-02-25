app.controller('CreateReservationController', function ($rootScope, $scope, $stateParams, $http, $state, $mdDialog, $mdpDatePicker, $mdpTimePicker, restaurantsService, areasService) {

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.restaurantSelected = function(restaurant) {
        $scope.restaurant  = restaurant;
        $scope.data.selectedIndex = 1;
        areasService.listByRestaurant(restaurant.id, function(response) {
            $rootScope.$broadcast('SetAreas', response.data);
        });
    };

    $scope.next = function() {
        $scope.data.selectedIndex++;
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
