/*webpack.config.js*/
var path = require('path')
module.exports = {
  entry: {
    app: './app.js'
  },
  output: {
    path: path.resolve(__dirname, './dist'),
    publicPath: './dist/',
    filename: '[name].bundle.js',
    chunkFilename: '[name].chunk.js'
  },
  module: {
    rules: [
      {
        test: /\.css$/,
        use:[
          {
            loader: 'style-loader'
          },
          {
            loader: 'css-loader'
          }
        ],
        loaders: [
            　　　　{
            　　　　　　test: /\.(png|jpg)$/,
            　　　　　　loader: 'url-loader?limit=8192'
            　　　　},
                   {
            　　　　　　test: /\.html$/,
            　　　　　　loader: 'html-withimg-loader'
            　　　　}
          　　]
      }
    ]
  }
  
}