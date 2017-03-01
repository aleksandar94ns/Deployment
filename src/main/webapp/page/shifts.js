app.controller('ShiftsController', function ($scope, $http, $state, $filter, $location, $log, $rootScope, $mdDialog, shiftsService, employeeShiftsService) {

    $scope.page.current = 17;

    $scope.daySelection = {
        dayAhead: 7,
        dayBehind: -7
    };
    $scope.dateDaySelection = {
        dateDayAhead: new Date(),
        dateDayBehind: new Date()
    };
    $scope.employeeShiftsSearched = [];
    $scope.dayAh = 0;
    $scope.dayBh = 0;
    $scope.shifts = [];
    $scope.parsedShifts = [];
    $scope.employeeShifts = [];
    $scope.parsedEmployeeShifts = [];

    var loadEmployeeShifts = function () {
        employeeShiftsService.list(function (response) {
            $scope.employeeShifts = [];
            $scope.employeeShifts = response.data;
        })
    };

    loadEmployeeShifts();

    var loadShifts = function () {
        shiftsService.list(function (response) {
            $scope.shifts = [];
            $scope.parsedShifts = [];
            $scope.shifts = response.data;
            $scope.shifts.forEach(function (shift) {
                shift.startHour = moment(shift.startHour).format("HH:mm:ss");
                shift.endHour = moment(shift.endHour).format("HH:mm:ss");
                $scope.parsedShifts.push(shift);
            })
        });
    };

    loadShifts();

    $scope.addShift = function () {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createShift.html',
            controller: 'CreateShiftController'
        }).finally(function () {
            loadShifts();
        });
    };

    $scope.addEmployeeShift = function () {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createEmployeeShift.html',
            controller: 'CreateEmployeeShiftController'
        }).finally(function () {
            loadEmployeeShifts();
        });
    };

    $scope.searchShifts = function () {
        employeeShiftsService.list(function (response) {
            $scope.employeeShifts = [];
            $scope.parsedEmployeeShifts = [];
            $scope.employeeShifts = response.data;
            $scope.employeeShifts.forEach(function (shift) {
                $scope.parsedEmployeeShifts.push(shift);
            });
            var weekDay = new Date();
            weekDay.setDate($scope.daySelection.dayAhead);
            var weekBehind = new Date();
            weekBehind.setDate(-7);
            $scope.employeeShifts = [];
            $scope.parsedEmployeeShifts.forEach(function (parsedShift) {
                //parsedShift.date >= +weekBehind
                if (parsedShift.date <= +$scope.dateDaySelection.dateDayAhead && parsedShift.date >= +$scope.dateDaySelection.dateDayBehind) {
                    $scope.employeeShifts.push(parsedShift);
                }
            });
        });
    };


    // Calendar demo
    $scope.selectedDate = null;
    $scope.firstDayOfWeek = 1;
    $scope.setDirection = function (direction) {
        $scope.direction = direction;
    };

    $scope.dayClick = function (date) {

    };

    var html = null;

    $scope.setDayContent = function (date) {
        // You would inject any HTML you wanted for
        // that particular date here.
        employeeShiftsService.search(date, function (response) {
            if(response.data.length != 0){
                $scope.employeeShiftsSearched = response.date;
                return html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';
            }
        });
    };
});