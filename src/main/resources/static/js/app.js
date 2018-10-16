var app = angular.module('app',[]);

app.controller('StockCRUDCtrl', ['$scope','StockCRUDService', function ($scope,StockCRUDService) {
	  
	$scope.onloadFun = function() {
		console.log('Onload is called');
		$scope.getAllStocks();
      }
	
    $scope.updateStock = function (stock) {
    	StockCRUDService.updateStock(stock)
          .then(function success(response){
        	  console.log(' update stock response is ', response);
        	  $scope.updateStockSuccess = 'Stock is updated!';
        	  $scope.updateStockError='';
              $scope.errorCode = '';
          },
          function error(response){
        	  console.log(' update stock response is ', response);
        	  $scope.updateStockSuccess = '';
        	  $scope.errorCode = response.data.errorCode;
              $scope.updateStockError = response.data.message;
          });
    }
    
    $scope.getStock = function () {
        var id = $scope.stock.id;
        StockCRUDService.getStock($scope.stock.id)
          .then(function success(response){
        	  console.log(' Get stock response is ', response);
              $scope.stock = response.data;
              $scope.stock.id = id;
              $scope.message='';
              $scope.errorCode= '';
          },
          function error (response ){
        	  console.log(' Get stock response is ', response);
              $scope.message = response.data.message;
              $scope.errorCode= response.data.errorCode;
              
          });
    }
    
    $scope.addStock = function (stock) {
        if (stock != null) {
        	StockCRUDService.addStock(stock)
              .then (function success(response){
            	  console.log(' Add stock response is ', response);
            	  $scope.stocks.push(response.data);
                  $scope.addStockSuccess = 'Stock added!';
                  $scope.addStockError='';
                  $scope.errorMessage = '';
              },
              function error(response){
            	  console.log(' Add stock error response is ', response);
            	  $scope.addStockSuccess = '';
                  $scope.errorCode = response.data.errorCode
                  $scope.addStockError = response.data.message;
            });
        }
        else {
            $scope.errorCode = 'Validation error';
            $scope.message = 'Please enter a name and current price';
            
        }
    }
    
    $scope.getAllStocks = function () {
        StockCRUDService.getAllStocks()
          .then(function success(response){
        	  console.log(' Get all stock response is ', response);
              $scope.stocks = response.data;
              $scope.message='';
              $scope.errorCode = '';
          },
          function error (response ){
        	  console.log(' Get all stock response is ', response);
              $scope.message='';
              $scope.errorCode = 'Error getting stocks!';
          });
    }

}]);

app.service('StockCRUDService',['$http', function ($http) {
	
    this.getStock = function getUser(userId){
        return $http({
          method: 'GET',
          url: 'api/stocks/'+stockId
        });
	}
	
    this.addStock = function addUser(stock){
        return $http({
          method: 'POST',
          url: 'api/stocks/',
          data: {name:stock.name, currentPrice:stock.currentPrice}
        });
    }
	
    this.updateStock = function updateUser(stock){
    	console.log('Request to update the stock ', stock);
        return $http({
          method: 'PUT',
          url: 'api/stocks/'+stock.id,
          data: {name:stock.name, currentPrice:stock.currentPrice}
        })
    }
	
    this.getAllStocks = function getAllStocks(){
        return $http({
          method: 'GET',
          url: 'api/stocks/'
        });
    }

}]);