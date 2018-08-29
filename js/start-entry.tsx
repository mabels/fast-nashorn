// import * as uuid from 'uuid';
// import * as React from 'react';
// import * as ReactDOM from 'react-dom';
// import { renderToString } from "react-dom/server";
import { h } from 'preact';
import { render as renderToString } from 'preact-render-to-string';

export function setTimeout(cb: () => void, t: number) {
    cb();
}
// const uuid = {
//  id: 4711,
//   v4: function() {
//     return ++(uuid.id);
//   }
// }

function EmptySizedArray<T>(c: number): (T | undefined)[] {
    const ret = [];
    for (let i = 0; i < c; ++i) {
        ret.push(undefined);
    }
    return ret;
}

function Item({item, id}: {item: number, key: string, id: () => string } ) {
  const key = id();
  return <li key={key}>{item}:{key}</li>;
}

function Level({level, count, id}: {level: number, key: string, count: number, id: () => string }) {
    return <ul key={id()}>
        {EmptySizedArray(count).map((_, i) => <Item key={id()} item={i} id={id} />)}
    </ul>
}
function TopLevel({count, id}: { count: number, id: () => string }) {
    return <ul key={id()}>
        {EmptySizedArray(count).map((_, i) => <Level key={id()} count={3} level={i} id={id} />)}
    </ul>;
}

export default function main() {
  let id = 4711;
  const ret = renderToString(<TopLevel count={100} id={() => ''+id++} />);
  return ret;
}

// > global.ReactNashorn()
// 
// Warning: Each child in an array or iterator should have a unique "key" prop.
// Check the top-level render call using <ul>. See https://fb.me/react-warning-keys for more information.
//     in Item
//     in Level
//     in ul
//     in TopLevel

