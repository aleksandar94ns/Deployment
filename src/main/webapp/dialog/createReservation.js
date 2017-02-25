app.controller('CreateReservationController', function ($rootScope, $scope, $stateParams, $http, $state, $mdDialog, $mdpDatePicker, $mdpTimePicker, restaurantsService, areasService) {

    $scope.reservation = {};
    $scope.reservation.arrivalDate = new Date();
    $scope.reservation.departureDate = new Date();

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.restaurantSelected = function(restaurant) {
        $scope.reservation.restaurant  = restaurant;
        $scope.data.selectedIndex = 1;
    };

    $scope.next = function() {
        switch ($scope.data.selectedIndex) {
            case 1:
                areasService.listByRestaurantAndAvailability($scope.reservation.restaurant.id, moment($scope.reservation.arrivalDate).format('YYYY-MM-DD HH:mm:ss.S'),  moment($scope.reservation.departureDate).format('YYYY-MM-DD HH:mm:ss.S'), function(response) {
                    $rootScope.$broadcast('SetAreas', response.data);
                });
                break;
            case 2:
                break;
            case 3:
                break;
        }
        $scope.data.selectedIndex++;
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
