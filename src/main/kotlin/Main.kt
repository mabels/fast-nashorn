import java.util.concurrent.BlockingQueue
import java.util.concurrent.CountDownLatch
import java.util.concurrent.LinkedBlockingQueue
import javax.script.Bindings
import kotlin.system.measureNanoTime
import kotlin.system.measureTimeMillis

import com.google.common.math.StatsAccumulator

data class WorkerLoops(
        val worker: Int,
        val loops: Int,
        val engines: Int,
        val bindings: Int = Math.ceil((0.0+worker)/engines).toInt()) {
}

data class Result(
        val id: Long,
        val workerLoop: WorkerLoops = WorkerLoops(0, 0, 0),
        var workerTime: Long = 0,
        var workerId: Long = 0,
        var workerCount: Long = 0,
        var reactStringLen: Int = 0,
        var takeBindingsTime: Long = 0,
        var je: JavascriptEngine? = null) {
}



class Worker(val startCountDownLatch: CountDownLatch,
             val jobs: BlockingQueue<Result>, val results: BlockingQueue<Result>,
             val engine: JavascriptEngine,
             var actions: Long = 0) : Runnable {

    val ref_preact = """<ul><ul><li>0:4816</li><li>1:4817</li><li>2:4818</li></ul><ul><li>0:4823</li><li>1:4824</li><li>2:4825</li></ul><ul><li>0:4830</li><li>1:4831</li><li>2:4832</li></ul><ul><li>0:4837</li><li>1:4838</li><li>2:4839</li></ul><ul><li>0:4844</li><li>1:4845</li><li>2:4846</li></ul><ul><li>0:4851</li><li>1:4852</li><li>2:4853</li></ul><ul><li>0:4858</li><li>1:4859</li><li>2:4860</li></ul><ul><li>0:4865</li><li>1:4866</li><li>2:4867</li></ul><ul><li>0:4872</li><li>1:4873</li><li>2:4874</li></ul><ul><li>0:4879</li><li>1:4880</li><li>2:4881</li></ul><ul><li>0:4886</li><li>1:4887</li><li>2:4888</li></ul><ul><li>0:4893</li><li>1:4894</li><li>2:4895</li></ul><ul><li>0:4900</li><li>1:4901</li><li>2:4902</li></ul><ul><li>0:4907</li><li>1:4908</li><li>2:4909</li></ul><ul><li>0:4914</li><li>1:4915</li><li>2:4916</li></ul><ul><li>0:4921</li><li>1:4922</li><li>2:4923</li></ul><ul><li>0:4928</li><li>1:4929</li><li>2:4930</li></ul><ul><li>0:4935</li><li>1:4936</li><li>2:4937</li></ul><ul><li>0:4942</li><li>1:4943</li><li>2:4944</li></ul><ul><li>0:4949</li><li>1:4950</li><li>2:4951</li></ul><ul><li>0:4956</li><li>1:4957</li><li>2:4958</li></ul><ul><li>0:4963</li><li>1:4964</li><li>2:4965</li></ul><ul><li>0:4970</li><li>1:4971</li><li>2:4972</li></ul><ul><li>0:4977</li><li>1:4978</li><li>2:4979</li></ul><ul><li>0:4984</li><li>1:4985</li><li>2:4986</li></ul><ul><li>0:4991</li><li>1:4992</li><li>2:4993</li></ul><ul><li>0:4998</li><li>1:4999</li><li>2:5000</li></ul><ul><li>0:5005</li><li>1:5006</li><li>2:5007</li></ul><ul><li>0:5012</li><li>1:5013</li><li>2:5014</li></ul><ul><li>0:5019</li><li>1:5020</li><li>2:5021</li></ul><ul><li>0:5026</li><li>1:5027</li><li>2:5028</li></ul><ul><li>0:5033</li><li>1:5034</li><li>2:5035</li></ul><ul><li>0:5040</li><li>1:5041</li><li>2:5042</li></ul><ul><li>0:5047</li><li>1:5048</li><li>2:5049</li></ul><ul><li>0:5054</li><li>1:5055</li><li>2:5056</li></ul><ul><li>0:5061</li><li>1:5062</li><li>2:5063</li></ul><ul><li>0:5068</li><li>1:5069</li><li>2:5070</li></ul><ul><li>0:5075</li><li>1:5076</li><li>2:5077</li></ul><ul><li>0:5082</li><li>1:5083</li><li>2:5084</li></ul><ul><li>0:5089</li><li>1:5090</li><li>2:5091</li></ul><ul><li>0:5096</li><li>1:5097</li><li>2:5098</li></ul><ul><li>0:5103</li><li>1:5104</li><li>2:5105</li></ul><ul><li>0:5110</li><li>1:5111</li><li>2:5112</li></ul><ul><li>0:5117</li><li>1:5118</li><li>2:5119</li></ul><ul><li>0:5124</li><li>1:5125</li><li>2:5126</li></ul><ul><li>0:5131</li><li>1:5132</li><li>2:5133</li></ul><ul><li>0:5138</li><li>1:5139</li><li>2:5140</li></ul><ul><li>0:5145</li><li>1:5146</li><li>2:5147</li></ul><ul><li>0:5152</li><li>1:5153</li><li>2:5154</li></ul><ul><li>0:5159</li><li>1:5160</li><li>2:5161</li></ul><ul><li>0:5166</li><li>1:5167</li><li>2:5168</li></ul><ul><li>0:5173</li><li>1:5174</li><li>2:5175</li></ul><ul><li>0:5180</li><li>1:5181</li><li>2:5182</li></ul><ul><li>0:5187</li><li>1:5188</li><li>2:5189</li></ul><ul><li>0:5194</li><li>1:5195</li><li>2:5196</li></ul><ul><li>0:5201</li><li>1:5202</li><li>2:5203</li></ul><ul><li>0:5208</li><li>1:5209</li><li>2:5210</li></ul><ul><li>0:5215</li><li>1:5216</li><li>2:5217</li></ul><ul><li>0:5222</li><li>1:5223</li><li>2:5224</li></ul><ul><li>0:5229</li><li>1:5230</li><li>2:5231</li></ul><ul><li>0:5236</li><li>1:5237</li><li>2:5238</li></ul><ul><li>0:5243</li><li>1:5244</li><li>2:5245</li></ul><ul><li>0:5250</li><li>1:5251</li><li>2:5252</li></ul><ul><li>0:5257</li><li>1:5258</li><li>2:5259</li></ul><ul><li>0:5264</li><li>1:5265</li><li>2:5266</li></ul><ul><li>0:5271</li><li>1:5272</li><li>2:5273</li></ul><ul><li>0:5278</li><li>1:5279</li><li>2:5280</li></ul><ul><li>0:5285</li><li>1:5286</li><li>2:5287</li></ul><ul><li>0:5292</li><li>1:5293</li><li>2:5294</li></ul><ul><li>0:5299</li><li>1:5300</li><li>2:5301</li></ul><ul><li>0:5306</li><li>1:5307</li><li>2:5308</li></ul><ul><li>0:5313</li><li>1:5314</li><li>2:5315</li></ul><ul><li>0:5320</li><li>1:5321</li><li>2:5322</li></ul><ul><li>0:5327</li><li>1:5328</li><li>2:5329</li></ul><ul><li>0:5334</li><li>1:5335</li><li>2:5336</li></ul><ul><li>0:5341</li><li>1:5342</li><li>2:5343</li></ul><ul><li>0:5348</li><li>1:5349</li><li>2:5350</li></ul><ul><li>0:5355</li><li>1:5356</li><li>2:5357</li></ul><ul><li>0:5362</li><li>1:5363</li><li>2:5364</li></ul><ul><li>0:5369</li><li>1:5370</li><li>2:5371</li></ul><ul><li>0:5376</li><li>1:5377</li><li>2:5378</li></ul><ul><li>0:5383</li><li>1:5384</li><li>2:5385</li></ul><ul><li>0:5390</li><li>1:5391</li><li>2:5392</li></ul><ul><li>0:5397</li><li>1:5398</li><li>2:5399</li></ul><ul><li>0:5404</li><li>1:5405</li><li>2:5406</li></ul><ul><li>0:5411</li><li>1:5412</li><li>2:5413</li></ul><ul><li>0:5418</li><li>1:5419</li><li>2:5420</li></ul><ul><li>0:5425</li><li>1:5426</li><li>2:5427</li></ul><ul><li>0:5432</li><li>1:5433</li><li>2:5434</li></ul><ul><li>0:5439</li><li>1:5440</li><li>2:5441</li></ul><ul><li>0:5446</li><li>1:5447</li><li>2:5448</li></ul><ul><li>0:5453</li><li>1:5454</li><li>2:5455</li></ul><ul><li>0:5460</li><li>1:5461</li><li>2:5462</li></ul><ul><li>0:5467</li><li>1:5468</li><li>2:5469</li></ul><ul><li>0:5474</li><li>1:5475</li><li>2:5476</li></ul><ul><li>0:5481</li><li>1:5482</li><li>2:5483</li></ul><ul><li>0:5488</li><li>1:5489</li><li>2:5490</li></ul><ul><li>0:5495</li><li>1:5496</li><li>2:5497</li></ul><ul><li>0:5502</li><li>1:5503</li><li>2:5504</li></ul><ul><li>0:5509</li><li>1:5510</li><li>2:5511</li></ul></ul>"""
    val ref_react = """<ul data-reactroot=""><ul><li>0<!-- -->:<!-- -->4816</li><li>1<!-- -->:<!-- -->4817</li><li>2<!-- -->:<!-- -->4818</li></ul><ul><li>0<!-- -->:<!-- -->4823</li><li>1<!-- -->:<!-- -->4824</li><li>2<!-- -->:<!-- -->4825</li></ul><ul><li>0<!-- -->:<!-- -->4830</li><li>1<!-- -->:<!-- -->4831</li><li>2<!-- -->:<!-- -->4832</li></ul><ul><li>0<!-- -->:<!-- -->4837</li><li>1<!-- -->:<!-- -->4838</li><li>2<!-- -->:<!-- -->4839</li></ul><ul><li>0<!-- -->:<!-- -->4844</li><li>1<!-- -->:<!-- -->4845</li><li>2<!-- -->:<!-- -->4846</li></ul><ul><li>0<!-- -->:<!-- -->4851</li><li>1<!-- -->:<!-- -->4852</li><li>2<!-- -->:<!-- -->4853</li></ul><ul><li>0<!-- -->:<!-- -->4858</li><li>1<!-- -->:<!-- -->4859</li><li>2<!-- -->:<!-- -->4860</li></ul><ul><li>0<!-- -->:<!-- -->4865</li><li>1<!-- -->:<!-- -->4866</li><li>2<!-- -->:<!-- -->4867</li></ul><ul><li>0<!-- -->:<!-- -->4872</li><li>1<!-- -->:<!-- -->4873</li><li>2<!-- -->:<!-- -->4874</li></ul><ul><li>0<!-- -->:<!-- -->4879</li><li>1<!-- -->:<!-- -->4880</li><li>2<!-- -->:<!-- -->4881</li></ul><ul><li>0<!-- -->:<!-- -->4886</li><li>1<!-- -->:<!-- -->4887</li><li>2<!-- -->:<!-- -->4888</li></ul><ul><li>0<!-- -->:<!-- -->4893</li><li>1<!-- -->:<!-- -->4894</li><li>2<!-- -->:<!-- -->4895</li></ul><ul><li>0<!-- -->:<!-- -->4900</li><li>1<!-- -->:<!-- -->4901</li><li>2<!-- -->:<!-- -->4902</li></ul><ul><li>0<!-- -->:<!-- -->4907</li><li>1<!-- -->:<!-- -->4908</li><li>2<!-- -->:<!-- -->4909</li></ul><ul><li>0<!-- -->:<!-- -->4914</li><li>1<!-- -->:<!-- -->4915</li><li>2<!-- -->:<!-- -->4916</li></ul><ul><li>0<!-- -->:<!-- -->4921</li><li>1<!-- -->:<!-- -->4922</li><li>2<!-- -->:<!-- -->4923</li></ul><ul><li>0<!-- -->:<!-- -->4928</li><li>1<!-- -->:<!-- -->4929</li><li>2<!-- -->:<!-- -->4930</li></ul><ul><li>0<!-- -->:<!-- -->4935</li><li>1<!-- -->:<!-- -->4936</li><li>2<!-- -->:<!-- -->4937</li></ul><ul><li>0<!-- -->:<!-- -->4942</li><li>1<!-- -->:<!-- -->4943</li><li>2<!-- -->:<!-- -->4944</li></ul><ul><li>0<!-- -->:<!-- -->4949</li><li>1<!-- -->:<!-- -->4950</li><li>2<!-- -->:<!-- -->4951</li></ul><ul><li>0<!-- -->:<!-- -->4956</li><li>1<!-- -->:<!-- -->4957</li><li>2<!-- -->:<!-- -->4958</li></ul><ul><li>0<!-- -->:<!-- -->4963</li><li>1<!-- -->:<!-- -->4964</li><li>2<!-- -->:<!-- -->4965</li></ul><ul><li>0<!-- -->:<!-- -->4970</li><li>1<!-- -->:<!-- -->4971</li><li>2<!-- -->:<!-- -->4972</li></ul><ul><li>0<!-- -->:<!-- -->4977</li><li>1<!-- -->:<!-- -->4978</li><li>2<!-- -->:<!-- -->4979</li></ul><ul><li>0<!-- -->:<!-- -->4984</li><li>1<!-- -->:<!-- -->4985</li><li>2<!-- -->:<!-- -->4986</li></ul><ul><li>0<!-- -->:<!-- -->4991</li><li>1<!-- -->:<!-- -->4992</li><li>2<!-- -->:<!-- -->4993</li></ul><ul><li>0<!-- -->:<!-- -->4998</li><li>1<!-- -->:<!-- -->4999</li><li>2<!-- -->:<!-- -->5000</li></ul><ul><li>0<!-- -->:<!-- -->5005</li><li>1<!-- -->:<!-- -->5006</li><li>2<!-- -->:<!-- -->5007</li></ul><ul><li>0<!-- -->:<!-- -->5012</li><li>1<!-- -->:<!-- -->5013</li><li>2<!-- -->:<!-- -->5014</li></ul><ul><li>0<!-- -->:<!-- -->5019</li><li>1<!-- -->:<!-- -->5020</li><li>2<!-- -->:<!-- -->5021</li></ul><ul><li>0<!-- -->:<!-- -->5026</li><li>1<!-- -->:<!-- -->5027</li><li>2<!-- -->:<!-- -->5028</li></ul><ul><li>0<!-- -->:<!-- -->5033</li><li>1<!-- -->:<!-- -->5034</li><li>2<!-- -->:<!-- -->5035</li></ul><ul><li>0<!-- -->:<!-- -->5040</li><li>1<!-- -->:<!-- -->5041</li><li>2<!-- -->:<!-- -->5042</li></ul><ul><li>0<!-- -->:<!-- -->5047</li><li>1<!-- -->:<!-- -->5048</li><li>2<!-- -->:<!-- -->5049</li></ul><ul><li>0<!-- -->:<!-- -->5054</li><li>1<!-- -->:<!-- -->5055</li><li>2<!-- -->:<!-- -->5056</li></ul><ul><li>0<!-- -->:<!-- -->5061</li><li>1<!-- -->:<!-- -->5062</li><li>2<!-- -->:<!-- -->5063</li></ul><ul><li>0<!-- -->:<!-- -->5068</li><li>1<!-- -->:<!-- -->5069</li><li>2<!-- -->:<!-- -->5070</li></ul><ul><li>0<!-- -->:<!-- -->5075</li><li>1<!-- -->:<!-- -->5076</li><li>2<!-- -->:<!-- -->5077</li></ul><ul><li>0<!-- -->:<!-- -->5082</li><li>1<!-- -->:<!-- -->5083</li><li>2<!-- -->:<!-- -->5084</li></ul><ul><li>0<!-- -->:<!-- -->5089</li><li>1<!-- -->:<!-- -->5090</li><li>2<!-- -->:<!-- -->5091</li></ul><ul><li>0<!-- -->:<!-- -->5096</li><li>1<!-- -->:<!-- -->5097</li><li>2<!-- -->:<!-- -->5098</li></ul><ul><li>0<!-- -->:<!-- -->5103</li><li>1<!-- -->:<!-- -->5104</li><li>2<!-- -->:<!-- -->5105</li></ul><ul><li>0<!-- -->:<!-- -->5110</li><li>1<!-- -->:<!-- -->5111</li><li>2<!-- -->:<!-- -->5112</li></ul><ul><li>0<!-- -->:<!-- -->5117</li><li>1<!-- -->:<!-- -->5118</li><li>2<!-- -->:<!-- -->5119</li></ul><ul><li>0<!-- -->:<!-- -->5124</li><li>1<!-- -->:<!-- -->5125</li><li>2<!-- -->:<!-- -->5126</li></ul><ul><li>0<!-- -->:<!-- -->5131</li><li>1<!-- -->:<!-- -->5132</li><li>2<!-- -->:<!-- -->5133</li></ul><ul><li>0<!-- -->:<!-- -->5138</li><li>1<!-- -->:<!-- -->5139</li><li>2<!-- -->:<!-- -->5140</li></ul><ul><li>0<!-- -->:<!-- -->5145</li><li>1<!-- -->:<!-- -->5146</li><li>2<!-- -->:<!-- -->5147</li></ul><ul><li>0<!-- -->:<!-- -->5152</li><li>1<!-- -->:<!-- -->5153</li><li>2<!-- -->:<!-- -->5154</li></ul><ul><li>0<!-- -->:<!-- -->5159</li><li>1<!-- -->:<!-- -->5160</li><li>2<!-- -->:<!-- -->5161</li></ul><ul><li>0<!-- -->:<!-- -->5166</li><li>1<!-- -->:<!-- -->5167</li><li>2<!-- -->:<!-- -->5168</li></ul><ul><li>0<!-- -->:<!-- -->5173</li><li>1<!-- -->:<!-- -->5174</li><li>2<!-- -->:<!-- -->5175</li></ul><ul><li>0<!-- -->:<!-- -->5180</li><li>1<!-- -->:<!-- -->5181</li><li>2<!-- -->:<!-- -->5182</li></ul><ul><li>0<!-- -->:<!-- -->5187</li><li>1<!-- -->:<!-- -->5188</li><li>2<!-- -->:<!-- -->5189</li></ul><ul><li>0<!-- -->:<!-- -->5194</li><li>1<!-- -->:<!-- -->5195</li><li>2<!-- -->:<!-- -->5196</li></ul><ul><li>0<!-- -->:<!-- -->5201</li><li>1<!-- -->:<!-- -->5202</li><li>2<!-- -->:<!-- -->5203</li></ul><ul><li>0<!-- -->:<!-- -->5208</li><li>1<!-- -->:<!-- -->5209</li><li>2<!-- -->:<!-- -->5210</li></ul><ul><li>0<!-- -->:<!-- -->5215</li><li>1<!-- -->:<!-- -->5216</li><li>2<!-- -->:<!-- -->5217</li></ul><ul><li>0<!-- -->:<!-- -->5222</li><li>1<!-- -->:<!-- -->5223</li><li>2<!-- -->:<!-- -->5224</li></ul><ul><li>0<!-- -->:<!-- -->5229</li><li>1<!-- -->:<!-- -->5230</li><li>2<!-- -->:<!-- -->5231</li></ul><ul><li>0<!-- -->:<!-- -->5236</li><li>1<!-- -->:<!-- -->5237</li><li>2<!-- -->:<!-- -->5238</li></ul><ul><li>0<!-- -->:<!-- -->5243</li><li>1<!-- -->:<!-- -->5244</li><li>2<!-- -->:<!-- -->5245</li></ul><ul><li>0<!-- -->:<!-- -->5250</li><li>1<!-- -->:<!-- -->5251</li><li>2<!-- -->:<!-- -->5252</li></ul><ul><li>0<!-- -->:<!-- -->5257</li><li>1<!-- -->:<!-- -->5258</li><li>2<!-- -->:<!-- -->5259</li></ul><ul><li>0<!-- -->:<!-- -->5264</li><li>1<!-- -->:<!-- -->5265</li><li>2<!-- -->:<!-- -->5266</li></ul><ul><li>0<!-- -->:<!-- -->5271</li><li>1<!-- -->:<!-- -->5272</li><li>2<!-- -->:<!-- -->5273</li></ul><ul><li>0<!-- -->:<!-- -->5278</li><li>1<!-- -->:<!-- -->5279</li><li>2<!-- -->:<!-- -->5280</li></ul><ul><li>0<!-- -->:<!-- -->5285</li><li>1<!-- -->:<!-- -->5286</li><li>2<!-- -->:<!-- -->5287</li></ul><ul><li>0<!-- -->:<!-- -->5292</li><li>1<!-- -->:<!-- -->5293</li><li>2<!-- -->:<!-- -->5294</li></ul><ul><li>0<!-- -->:<!-- -->5299</li><li>1<!-- -->:<!-- -->5300</li><li>2<!-- -->:<!-- -->5301</li></ul><ul><li>0<!-- -->:<!-- -->5306</li><li>1<!-- -->:<!-- -->5307</li><li>2<!-- -->:<!-- -->5308</li></ul><ul><li>0<!-- -->:<!-- -->5313</li><li>1<!-- -->:<!-- -->5314</li><li>2<!-- -->:<!-- -->5315</li></ul><ul><li>0<!-- -->:<!-- -->5320</li><li>1<!-- -->:<!-- -->5321</li><li>2<!-- -->:<!-- -->5322</li></ul><ul><li>0<!-- -->:<!-- -->5327</li><li>1<!-- -->:<!-- -->5328</li><li>2<!-- -->:<!-- -->5329</li></ul><ul><li>0<!-- -->:<!-- -->5334</li><li>1<!-- -->:<!-- -->5335</li><li>2<!-- -->:<!-- -->5336</li></ul><ul><li>0<!-- -->:<!-- -->5341</li><li>1<!-- -->:<!-- -->5342</li><li>2<!-- -->:<!-- -->5343</li></ul><ul><li>0<!-- -->:<!-- -->5348</li><li>1<!-- -->:<!-- -->5349</li><li>2<!-- -->:<!-- -->5350</li></ul><ul><li>0<!-- -->:<!-- -->5355</li><li>1<!-- -->:<!-- -->5356</li><li>2<!-- -->:<!-- -->5357</li></ul><ul><li>0<!-- -->:<!-- -->5362</li><li>1<!-- -->:<!-- -->5363</li><li>2<!-- -->:<!-- -->5364</li></ul><ul><li>0<!-- -->:<!-- -->5369</li><li>1<!-- -->:<!-- -->5370</li><li>2<!-- -->:<!-- -->5371</li></ul><ul><li>0<!-- -->:<!-- -->5376</li><li>1<!-- -->:<!-- -->5377</li><li>2<!-- -->:<!-- -->5378</li></ul><ul><li>0<!-- -->:<!-- -->5383</li><li>1<!-- -->:<!-- -->5384</li><li>2<!-- -->:<!-- -->5385</li></ul><ul><li>0<!-- -->:<!-- -->5390</li><li>1<!-- -->:<!-- -->5391</li><li>2<!-- -->:<!-- -->5392</li></ul><ul><li>0<!-- -->:<!-- -->5397</li><li>1<!-- -->:<!-- -->5398</li><li>2<!-- -->:<!-- -->5399</li></ul><ul><li>0<!-- -->:<!-- -->5404</li><li>1<!-- -->:<!-- -->5405</li><li>2<!-- -->:<!-- -->5406</li></ul><ul><li>0<!-- -->:<!-- -->5411</li><li>1<!-- -->:<!-- -->5412</li><li>2<!-- -->:<!-- -->5413</li></ul><ul><li>0<!-- -->:<!-- -->5418</li><li>1<!-- -->:<!-- -->5419</li><li>2<!-- -->:<!-- -->5420</li></ul><ul><li>0<!-- -->:<!-- -->5425</li><li>1<!-- -->:<!-- -->5426</li><li>2<!-- -->:<!-- -->5427</li></ul><ul><li>0<!-- -->:<!-- -->5432</li><li>1<!-- -->:<!-- -->5433</li><li>2<!-- -->:<!-- -->5434</li></ul><ul><li>0<!-- -->:<!-- -->5439</li><li>1<!-- -->:<!-- -->5440</li><li>2<!-- -->:<!-- -->5441</li></ul><ul><li>0<!-- -->:<!-- -->5446</li><li>1<!-- -->:<!-- -->5447</li><li>2<!-- -->:<!-- -->5448</li></ul><ul><li>0<!-- -->:<!-- -->5453</li><li>1<!-- -->:<!-- -->5454</li><li>2<!-- -->:<!-- -->5455</li></ul><ul><li>0<!-- -->:<!-- -->5460</li><li>1<!-- -->:<!-- -->5461</li><li>2<!-- -->:<!-- -->5462</li></ul><ul><li>0<!-- -->:<!-- -->5467</li><li>1<!-- -->:<!-- -->5468</li><li>2<!-- -->:<!-- -->5469</li></ul><ul><li>0<!-- -->:<!-- -->5474</li><li>1<!-- -->:<!-- -->5475</li><li>2<!-- -->:<!-- -->5476</li></ul><ul><li>0<!-- -->:<!-- -->5481</li><li>1<!-- -->:<!-- -->5482</li><li>2<!-- -->:<!-- -->5483</li></ul><ul><li>0<!-- -->:<!-- -->5488</li><li>1<!-- -->:<!-- -->5489</li><li>2<!-- -->:<!-- -->5490</li></ul><ul><li>0<!-- -->:<!-- -->5495</li><li>1<!-- -->:<!-- -->5496</li><li>2<!-- -->:<!-- -->5497</li></ul><ul><li>0<!-- -->:<!-- -->5502</li><li>1<!-- -->:<!-- -->5503</li><li>2<!-- -->:<!-- -->5504</li></ul><ul><li>0<!-- -->:<!-- -->5509</li><li>1<!-- -->:<!-- -->5510</li><li>2<!-- -->:<!-- -->5511</li></ul></ul>""";
    override fun run() {
//         val je = JavascriptEngineEval();
//         je.initialize();
//        val je = engines.take();
//        engines.put(je);
//        println(binding);
        // var myCount = 0L;
        val tid = Thread.currentThread().id;
        this.startCountDownLatch.countDown();
        while (true) {
            val x = jobs.take();
            var binding: Bindings? = null;
            x.takeBindingsTime = measureNanoTime {
                binding = engine.takeBindings();
            }
            x.workerTime = measureNanoTime {
                x.workerId = tid;
                x.workerCount = ++actions;
                val ret = engine.exec(binding, "ReactNashorn", x.workerId);

                x.reactStringLen = ret.length;
                val ref = ref_preact
                if (!ref.equals(ret)) {
                   println("$tid:REF:${ref.length}[$ref]");
                   println("$tid:RET:${ret.length}[$ret]");
                }
//                if ((ret.get("text") as String) != "hello world") {
//                    println("Error:text:${ret.keys}");
//                }
//                if (ret.get("id") as Long != x.workerId) {
//                    println("Error:id:${ret.get("id")!!::class}:${x.workerId}");
//                }
//                if (ret.get("cCount") != (++myCount + 0.0)) {
//                    println("Error:cCount:${ret.get("cCount")}:${myCount}");
//                }
            };
            engine.putBindings(binding);
//            if (x.workerTime > 311636) {
//                println(x.workerTime);
//            }
            results.put(x);
        }
    }
}

class Sequencer(val queue: BlockingQueue<Result>, val workerLoops: WorkerLoops, val items: Long = 100): Runnable {
    override fun run() {
        for(i in 1..items) {
            queue.put(Result(i, workerLoops));
        }
    }
}

class Resulter(val results: BlockingQueue<Result>,
               val countDownLatch: CountDownLatch,
               var totalWorkerTime: StatsAccumulator = StatsAccumulator(),
               var totalWorkerCount: Long = 0,
               var renderLength: Long = 0,
               var totalTakeBindingsTime: StatsAccumulator = StatsAccumulator() ): Runnable {

    override fun run() {
            do {
                val x = results.take();
                measureTimeMillis {
                    if (x.id >= 0) {
                        this.totalWorkerCount++;
                        this.totalWorkerTime.add(x.workerTime.toDouble());
                        this.renderLength += x.reactStringLen;
                        this.totalTakeBindingsTime.add(x.takeBindingsTime.toDouble());
//                println("Result: ${x.id} ${x.workerTime} ${x.workerCount}");
//                println("Resulter:countDown");
                        countDownLatch.countDown();
                    }
                }
            } while (x.id >= 0);
    }

    fun complete(wl: WorkerLoops, @Suppress("UNUSED_PARAMETER") rt: Runtime) {
        println("""
        worker:loop=${wl.worker}:${wl.loops}
        renderLen=${this.renderLength}
        totalTakeBindingsTime=${(this.totalTakeBindingsTime.sum()/this.totalWorkerCount).toLong()} mean=${this.totalTakeBindingsTime.mean().toLong()} stddev=${this.totalTakeBindingsTime.sampleStandardDeviation().toLong()} min=${this.totalTakeBindingsTime.min().toLong()} max=${this.totalTakeBindingsTime.max().toLong()}
        renderLenPerSecond=${((this.renderLength/this.totalWorkerCount)*(1000000000/(this.totalWorkerTime.sum()/this.totalWorkerCount))).toLong()}
        totalWorkerTime=${this.totalWorkerTime.sum().toLong()}/totalWorkerCount=${this.totalWorkerCount} => ${(this.totalWorkerTime.sum()/this.totalWorkerCount).toLong()} mean=${this.totalWorkerTime.mean().toLong()} stddev=${this.totalWorkerTime.sampleStandardDeviation().toLong()} min=${this.totalWorkerTime.min().toLong()} max=${this.totalWorkerTime.max().toLong()}
        -----------
        """.trimMargin());
//        System.out.println("Used: " + (rt.totalMemory() - rt.freeMemory()));
//        System.out.println("Free: " + rt.freeMemory());
//        System.out.println("Total: " + rt.totalMemory());
//        System.gc();
//        System.out.println("Used: " + (rt.totalMemory() - rt.freeMemory()));
//        System.out.println("Free: " + rt.freeMemory());
//        System.out.println("Total: " + rt.totalMemory());
    }
}



fun main(args: Array<String>) {
    println("nr cores:"+Runtime.getRuntime().availableProcessors());
//    arrayOf(WorkerLoops(6,36),
//            WorkerLoops(3,27),
//            WorkerLoops(2, 20),
//            WorkerLoops(1, 40)).forEach {
    arrayOf(WorkerLoops(1, 1 * 200, 1, 1),
            WorkerLoops(2, 2 * 800, 1, 2),
            WorkerLoops(3,3 * 800, 1, 3),
            WorkerLoops(6,6 * 800, 1, 5),
            WorkerLoops(18, 8 * 800, 1, 5)).forEach {
        val totalTime= measureNanoTime {
            val rt = Runtime.getRuntime();
            System.out.println("Used: " + (rt.totalMemory() - rt.freeMemory()));
            System.out.println("Free: " + rt.freeMemory());
            System.out.println("Total: " + rt.totalMemory());

            val jobs = LinkedBlockingQueue<Result>();
            val results = LinkedBlockingQueue<Result>();
            val countDownLatch = CountDownLatch(it.loops);
            val workers = arrayOfNulls<Thread>(it.worker);
            val startCountLatch = CountDownLatch(workers.size);

            // val engines = LinkedBlockingQueue<JavascriptEngine>();
            val engines = arrayOfNulls<JavascriptEngine>(it.engines);
            for (i in 0 until it.engines) {
                val je = JavascriptEngineEval();
                je.initialize();
                je.createBindings(it.bindings, arrayOf("ReactNashorn.js"))
                engines[i] = je;
                println("je created:" + je + " bindings:"+it.bindings);
            }


            workers.forEachIndexed { i, _ ->
                println("start ${i}/${workers.size} worker");
                val tworker = Thread(Worker(startCountLatch, jobs, results, engines[i % engines.size]!!));
                workers[i] = tworker;
                tworker.start();
            }
            startCountLatch.await();
            println("starting");
            val resulter = Resulter(results, countDownLatch)
            Thread(resulter).start();
            Thread(Sequencer(jobs, it, countDownLatch.count)).start();
            println("await-shutdown:${it.worker}:${it.loops}");
            countDownLatch.await();
            println("shutdown:${it.worker}:${it.loops}");
            workers.forEach({
                results.put(Result(-1))
            })
            resulter.complete(it, rt);
        }
        println("total.time:${it.worker}:${totalTime/1000000}");
    }
}
