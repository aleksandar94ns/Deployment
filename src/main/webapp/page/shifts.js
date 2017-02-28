app.controller('ShiftsController', function ($scope, $http, $state, $filter, $location, $log, $rootScope, $mdDialog, shiftsService, employeeShiftsService) {

    $scope.page.current = 17;

    $scope.selectUntil = [];
    $scope.selectFrom = [];
    $scope.shifts = [];
    $scope.parsedShifts = [];
    $scope.employeeShifts = [];
    $scope.parsedEmployeeShifts = [];
    $scope.found = null;

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

    $scope.addShift = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createShift.html',
            controller: 'CreateShiftController'
        }).finally(function () {
            loadShifts();
        });
    };

    $scope.addEmployeeShift = function() {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createEmployeeShift.html',
            controller: 'CreateEmployeeShiftController'
        }).finally(function () {
            loadEmployeeShifts();
        });
    };

    $scope.searchShifts = function() {
        employeeShiftsService.list(function (response) {
            $scope.employeeShifts = [];
            $scope.parsedEmployeeShifts = [];
            $scope.employeeShifts = response.data;
            $scope.employeeShifts.forEach(function (shift) {
                shift.date = moment(shift.date).format("YYYY-MM-DD");
                $scope.parsedEmployeeShifts.push(shift);
            });
            $scope.selectUntil = moment($scope.selectUntil).format("YYYY-MM-DD");
            $scope.selectFrom = moment($scope.selectFrom).format("YYYY-MM-DD");
            $scope.parsedEmployeeShifts.forEach(function (parsedShift) {
                $scope.employeeShifts = [];
                if (parsedShift.date < $scope.selectUntil){
                    $scope.employeeShifts.push(parsedShift);
                }
            });
        });
        $scope.selectUntil = [];
        $scope.selectFrom = [];
    };

    // Calendar demo
    $scope.selectedDate = null;
    $scope.firstDayOfWeek = 1;
    $scope.setDirection = function(direction) {
        $scope.direction = direction;
    };
    $scope.dayClick = function(date) {
        $mdDialog.show({
            parent: angular.element(document.body),
            templateUrl: 'dialog/createEmployeeShift.html',
            controller: 'CreateEmployeeShiftController',
            locals: {
                date: date
            }
        }).finally(function () {
            loadEmployeeShifts();
        });
        $scope.msg = "You clicked (prev) month " + date;
    };

    var html = null;

    $scope.setDayContent = function(date) {
        // You would inject any HTML you wanted for
        // that particular date here.
        employeeShiftsService.list(function (response) {
            /*$scope.employeeShifts = [];
            $scope.employeeShifts = response.data;
            for (var i in $scope.employeeShifts){
                $scope.dateToCompare = moment($scope.employeeShifts[i].date).format("YYYY-MM-DD");
                $scope.employeeShifts[i].date = moment($scope.employeeShifts[i].date).format("YYYY-MM-DD");
                date = moment(date).format("YYYY-MM-DD");
                if ($scope.dateToCompare == date){
                    $scope.found = $scope.employeeShifts[i].date;
                    //$scope.msg = "Date was found " + $filter("date")(date, "MMM d, y h:mm:ss a Z");
                    $scope.msg += ii + "_FOUND";
                    ii++;
                    html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';
                    break;
                }
                else {
                    html = null;
                }
            }*/
            /*$scope.employeeShifts.forEach(function (employeeShift) {
                employeeShift.date = moment(employeeShift.date).format("YYYY-MM-DD");
                date = moment(date).format("YYYY-MM-DD");
                if (employeeShift.date == date){
                    $scope.found = employeeShift.date;
                    //$scope.msg = "Date was found " + $filter("date")(date, "MMM d, y h:mm:ss a Z");
                    html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';
                    $scope.msg += i + "_FOUND";
                    i++;
                    return html;
                }
            });
            html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';*/
        });
        /*var datez = [];
        var datee1 = new Date();
        var datee2 = new Date();
        date = moment(date).format("YYYY-MM-DD");
        datee1 = moment(datee1).format("YYYY-MM-DD");
        datee2 = moment(datee2).format("YYYY-MM-DD");
        datez.push(datee1);
        datez.push(datee2);
        for (var d in datez){
            if (datez[d] == date){
                return html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';
            }
            else {
                html = null;
            }
        }*/
        /*if (date == datee){
            return html = '<div id="progressBarOuter"><div id="bytesLoaded"></div><div id="progressBar"></div></div><div id="currentTime">Found</div>';
        }
        else {
            html = null;
        }*/
        return html;
    };
});