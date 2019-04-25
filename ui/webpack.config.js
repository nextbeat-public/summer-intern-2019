/*
 * This file is part of the Nextbeat service.
 *
 * For the full copyright and license information,
 * please view the LICENSE file that was distributed with this source code.
 */

const webpack                 = require('webpack');
const path                    = require('path');
const TsconfigPathsPlugin     = require('tsconfig-paths-webpack-plugin');
const UglifyJsPlugin          = require('uglifyjs-webpack-plugin');
const ExtractTextPlugin       = require("extract-text-webpack-plugin");
const OptimizeCssAssetsPlugin = require("optimize-css-assets-webpack-plugin");

const PACKAGE = require('./package.json');

function getPlugin(mode, fname) {
  return [
    new webpack.ProvidePlugin({ jQuery: 'jquery', $: 'jquery' }),
    new ExtractTextPlugin({ filename: fname + '.css', allChunks: true })
  ];
}

module.exports = (env, argv) => {
  const mode  = argv.mode;
  const fname = PACKAGE.name.replace('.', '/') + '/[name]';
  return {
    plugins: getPlugin(mode, fname),
    entry: [ './src/main.ts', './scss/main.scss' ],
    resolve: {
      plugins: [
        new TsconfigPathsPlugin({ configFile: './tsconfig.json' })
      ],
      extensions:[ '.ts', '.js', '.png' ]
    },
    output:  {
      path:     path.resolve(__dirname, '../public'),
      filename: fname + '.js'
    },
    optimization: { minimizer: [ new UglifyJsPlugin() ] },
    performance: { hints: false },
    module: {
      rules: [
        {
          test: /\.ts$/,
          use: 'ts-loader'
        },
        {
          test: /\.scss$/,
          include: [
            path.resolve(__dirname, 'scss')
          ],
          use: ExtractTextPlugin.extract({
            fallback: 'style-loader',
            use: [
              { loader: 'css-loader' },
              { loader: 'sass-loader', options: {
                includePaths: [ 'node_modules' ]
              } }
            ]
          })
        },
        {
          test: /\.(jpg|jpeg|png)$/,
          use: [
            {
              loader: 'file-loader',
              options: {
                name: '[name].[ext]',
                outputPath: PACKAGE.name.replace('.', '/'),
                publicPath: './'
              }
            }
          ]
        }
      ]
    }
  };
}
