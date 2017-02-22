app.controller('NavigationController', function ($scope, $state, $location, $log, $rootScope, $mdSidenav, $http, authenticationService) {

    $scope.page = {
        title: 'Restaurant',
        current: -1
    };

    $scope.user = authenticationService.getUser();
    $scope.authService = authenticationService;

    $scope.logout = function () {
        authenticationService.logout(function () {
            $state.transitionTo('login');
        }, function () {
        });
    };

    $scope.toggleSidenav = buildToggler('left');

    function buildToggler(navID) {
        return function () {
            $mdSidenav(navID).toggle();
        }
    }

    $scope.isActive = function(pageIndex) {
        return $scope.page.current === pageIndex;
    };

    $scope.goToHome = function () {
        $state.transitionTo('navigation.home');
        $mdSidenav('left').close();
    };
    $scope.goToRestaurantManagers = function() {
        $state.transitionTo('navigation.restaurantManagers');
        $mdSidenav('left').close();
    };

    $scope.goToSystemManagers = function() {
        $state.transitionTo('navigation.systemManagers');
        $mdSidenav('left').close();
    };

    $scope.goToRestaurants = function () {
        $state.transitionTo('navigation.restaurants');
        $mdSidenav('left').close();
    };

    $scope.goToProfile = function () {
        $state.transitionTo('navigation.profile');
        $mdSidenav('left').close();
    };

    //--------------------------------------
    $scope.goToWorkingPlace = function () {
        $state.transitionTo('navigation.workingPlace.workingHours');
        $mdSidenav('left').close();
        $scope.page.current = 3;
    };
    //-----------------------------------------
});