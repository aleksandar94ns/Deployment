


app.controller('WorkingAreaController', WorkingAreaController);

function WorkingAreaController(authenticationService, $mdDialog) {


    this.user = authenticationService.getUser();
    this.authService = authenticationService;
    this.isOpen = false;
    this.selectedMode = 'md-fling';
    this.selectedDirection = 'up';

    this.takeOrder = function($event) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/order.html',
            controller: OrderController,
            controllerAs: 'ctrl',
            targetEvent: $event,
            clickOutsideToClose:true
        })
    }

}

function OrderController ($timeout, $q, $scope, $mdDialog) {
    var self = this;

    // list of `state` value/display objects
    self.states        = loadAll();
    self.querySearch   = querySearch;

    // ******************************
    // Template methods
    // ******************************

    self.cancel = function($event) {
        $mdDialog.cancel();
    };
    self.finish = function($event) {
        $mdDialog.hide();
    };

    // ******************************
    // Internal methods
    // ******************************

    /**
     * Search for states... use $timeout to simulate
     * remote dataservice call.
     */
    function querySearch (query) {
        return query ? self.states.filter( createFilterFor(query) ) : self.states;
    }

    /**
     * Build `states` list of key/value pairs
     */
    function loadAll() {
        var allStates = 'Alabama, Alaska, Arizona, Arkansas, California, Colorado, Connecticut, Delaware,\
              Florida, Georgia, Hawaii, Idaho, Illinois, Indiana, Iowa, Kansas, Kentucky, Louisiana,\
              Maine, Maryland, Massachusetts, Michigan, Minnesota, Mississippi, Missouri, Montana,\
              Nebraska, Nevada, New Hampshire, New Jersey, New Mexico, New York, North Carolina,\
              North Dakota, Ohio, Oklahoma, Oregon, Pennsylvania, Rhode Island, South Carolina,\
              South Dakota, Tennessee, Texas, Utah, Vermont, Virginia, Washington, West Virginia,\
              Wisconsin, Wyoming';

        return allStates.split(/, +/g).map( function (state) {
            return {
                value: state.toLowerCase(),
                display: state
            };
        });
    }

    /**
     * Create filter function for a query string
     */
    function createFilterFor(query) {
        var lowercaseQuery = angular.lowercase(query);

        return function filterFn(state) {
            return (state.value.indexOf(lowercaseQuery) === 0);
        };

    }
}

