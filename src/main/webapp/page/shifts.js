app.controller('ShiftsController', function ($scope, $http, $state, $filter, $location, $log, $rootScope, $mdDialog, shiftsService) {

    $scope.page.current = 17;

    $scope.shifts = [];
    $scope.parsedShifts = [];

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

    // Calendar demo
    $scope.selectedDate = null;
    $scope.firstDayOfWeek = 0;
    $scope.setDirection = function(direction) {
        $scope.direction = direction;
    };
    $scope.dayClick = function(date) {
        $scope.msg = "You clicked " + $filter("date")(date, "MMM d, y h:mm:ss a Z");
    };
    $scope.prevMonth = function(data) {
        $scope.msg = "You clicked (prev) month " + data.month + ", " + data.year;
    };
    $scope.nextMonth = function(data) {
        $scope.msg = "You clicked (next) month " + data.month + ", " + data.year;
    };
    $scope.setDayContent = function(date) {
        // You would inject any HTML you wanted for
        // that particular date here.
        return "<p></p>";
    };
});