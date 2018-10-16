var app = angular.module('app', []);

app.controller('StockCRUDCtrl', [
		'$scope',
		'StockCRUDService',
		function($scope, StockCRUDService) {

			$scope.onloadFun = function() {
				$scope.getAllStocks();
				$scope.stateChanged = false;
			}

			$scope.updateStock = function(stock) {
				StockCRUDService.updateStock(stock).then(
						function success(response) {
							$scope.updateStockSuccess = 'Stock is updated!';
							$scope.updateStockError = '';
							$scope.errorCode = '';
						}, function error(response) {
							$scope.updateStockSuccess = '';
							$scope.errorCode = response.data.errorCode;
							$scope.updateStockError = response.data.message;
						});

			}

			$scope.getStock = function() {
				var id = $scope.stock.id;
				StockCRUDService.getStock($scope.stock.id).then(
						function success(response) {
							$scope.stock = response.data;
							$scope.stock.id = id;
							$scope.message = '';
							$scope.errorCode = '';
						}, function error(response) {
							$scope.message = response.data.message;
							$scope.errorCode = response.data.errorCode;

						});
			}

			$scope.addStock = function(stock) {
				$scope.stateChanged = true;
				if (stock != null && stock.name && stock.currentPrice) {
					StockCRUDService.addStock(stock).then(
							function success(response) {
								$scope.stocks.push(response.data);
								$scope.addStockSuccess = 'Stock added!';
								$scope.addStockError = '';
								$scope.errorMessage = '';
							}, function error(response) {
								$scope.addStockSuccess = '';
								$scope.errorCode = response.data.errorCode
								$scope.addStockError = response.data.message;
							});
				}

			}

			$scope.getAllStocks = function() {
				StockCRUDService.getAllStocks().then(
						function success(response) {
							$scope.stocks = response.data;
							$scope.message = '';
							$scope.errorCode = '';
						}, function error(response) {
							$scope.message = '';
							$scope.errorCode = 'Error getting stocks!';
						});
			}

		} ]);

app.service('StockCRUDService', [ '$http', function($http) {

	this.getStock = function getUser(userId) {
		return $http({
			method : 'GET',
			url : 'api/stocks/' + stockId
		});
	}

	this.addStock = function addUser(stock) {
		return $http({
			method : 'POST',
			url : 'api/stocks/',
			data : {
				name : stock.name,
				currentPrice : stock.currentPrice
			}
		});
	}

	this.updateStock = function updateUser(stock) {
		return $http({
			method : 'PUT',
			url : 'api/stocks/' + stock.id,
			data : {
				name : stock.name,
				currentPrice : stock.currentPrice
			}
		})
	}

	this.getAllStocks = function getAllStocks() {
		return $http({
			method : 'GET',
			url : 'api/stocks/'
		});
	}

} ]);