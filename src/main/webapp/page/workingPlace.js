app.controller('WorkingPlaceController', WorkingPlaceController);

function WorkingPlaceController($scope, $state, authenticationService) {

    $scope.authService = authenticationService;
    $scope.goToWorkingArea = function() {
        $state.transitionTo('navigation.workingPlace.workingArea');

    }

    $scope.goToWorkingHours = function() {
        $state.transitionTo('navigation.workingPlace.workingHours');
    }
    $scope.goToDrinks = function() {
        $state.transitionTo('navigation.workingPlace.drinks');
    }
    $scope.goToSalads = function() {
        $state.transitionTo('navigation.workingPlace.salads');
    }
    $scope.goToCookedMeals = function() {
        $state.transitionTo('navigation.workingPlace.cookedMeals');
    }
    $scope.goToGrillMeals = function() {
        $state.transitionTo('navigation.workingPlace.grillMeals');
    }

};

