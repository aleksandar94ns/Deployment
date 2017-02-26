app.controller('CreateReservationController', function ($rootScope, $scope, $stateParams, $http, $state, $mdDialog, $mdpDatePicker, $mdpTimePicker, restaurantsService, areasService, reservationsService, friendsService) {

    $scope.reservation = {};
    $scope.reservation.arrivalDate = new Date();
    $scope.reservation.departureDate = new Date();
    $scope.reservation.restaurantTables = [];
    $scope.reservation.guests = [];

    restaurantsService.list(function(response) {
        $scope.restaurants = response.data;
    });

    $scope.restaurantSelected = function(restaurant) {
        $scope.reservation.restaurant  = restaurant;
        $scope.data.selectedIndex = 1;
    };

    $scope.$on('SelectedTables', function (event, data) {
        $scope.reservation.restaurantTables = data;
    });

    friendsService.listPotentialFriends(function(response) {
        $scope.users = response.data;
    });

    $scope.queryUsers = function (searchText) {
        var queryResults = [];
        for (var i = 0; i < $scope.users.length; i++) {
            if ($scope.users[i].firstName.toLowerCase().match(searchText.toLowerCase()) ||
                $scope.users[i].lastName.toLowerCase().match(searchText.toLowerCase()) ||
                searchText.toLowerCase().match($scope.users[i].firstName.toLowerCase()) ||
                searchText.toLowerCase().match($scope.users[i].lastName.toLowerCase())) {
                queryResults.push($scope.users[i]);
            }
        }
        return queryResults;
    };

    $scope.next = function() {
        switch ($scope.data.selectedIndex) {
            case 1:
                areasService.listByRestaurantAndAvailability($scope.reservation.restaurant.id, moment($scope.reservation.arrivalDate).format('YYYY-MM-DD HH:mm:ss.S'),  moment($scope.reservation.departureDate).format('YYYY-MM-DD HH:mm:ss.S'), function(response) {
                    $rootScope.$broadcast('SetAreas', response.data);
                });
                break;
            case 2:
                reservationsService.create($scope.reservation, function () {
                    $mdDialog.hide();
                });
                break;
            case 3:
                reservationsService.create($scope.reservation, function () {
                    $mdDialog.hide();
                });
                break;
        }
        $scope.data.selectedIndex++;
    };

    $scope.inviteFriends = function() {
        $scope.data.selectedIndex++;
    };

    $scope.close = function () {
        $mdDialog.hide();
    };
});
