const webpack = require('webpack');
const Path = require('path');

module.exports = {
  mode: 'development',
  entry: {
    ReactNashorn: './js/start-entry.tsx',
//    React: require.resolve('react'),
//    ReactDOM: require.resolve('react-dom')
  },
  module: {
    rules: [
      {
        test: /\.tsx?$/,
        loader: 'ts-loader',
        options: {
          transpileOnly: true
        }
      }
    ]
  },
  externals: {
//    react: 'React',
//    'react-dom': 'ReactDOM'
  },
  resolve: {
    extensions: ['.ts', '.tsx', '.js']
  },
  output: {
    filename: '[name].js',
    library: '[name]',
    libraryTarget: 'assign',
    libraryExport: 'default',
    path: Path.join(__dirname, 'src', 'main', 'resources')
  },
  plugins: [
    new webpack.IgnorePlugin(/jsdom*|jest*/)
  ]
};
